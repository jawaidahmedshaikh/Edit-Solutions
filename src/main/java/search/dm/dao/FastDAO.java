/*
 * FastDAO.java   Version 2.0   05/03/2004
 *
 * User: gfrosti
 * Date: May 03, 2004
 * Time: 11:23:14 AM
 *
 * (c) 2000-2004 Systems Engineering Group, LLC. All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential. Any use is
 * subject to the license agreement.
 */
package search.dm.dao;

import edit.common.vo.SearchResponseVO;
import edit.common.vo.SearchResponseContractInfo;
import edit.services.db.AbstractFastDAO;
import edit.services.db.ConnectionFactory;
import edit.services.db.DBTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import fission.utility.Util;
import contract.Segment;


public class FastDAO extends AbstractFastDAO
{
    private final String POOLNAME;

    private final DBTable CLIENT_DETAIL_DBTABLE;
    private final DBTable CLIENT_ROLE_DBTABLE;
    private final DBTable CONTRACT_CLIENT_DBTABLE;
    private final DBTable SEGMENT_DBTABLE;
    private final DBTable COMPANY_STRUCTURE_DBTABLE;
    private final DBTable CONTRACT_GROUP_DBTABLE;

    private final String CLIENT_DETAIL_TABLENAME;
    private final String CLIENT_ROLE_TABLENAME;
    private final String CONTRACT_CLIENT_TABLENAME;
    private final String SEGMENT_TABLENAME;
    private final String COMPANY_STRUCTURE_TABLENAME;
    private final String CONTRACT_GROUP_TABLENAME;


    public FastDAO()
    {
        POOLNAME  = ConnectionFactory.EDITSOLUTIONS_POOL;

        CLIENT_DETAIL_DBTABLE     = DBTable.getDBTableForTable("ClientDetail");
        CLIENT_ROLE_DBTABLE       = DBTable.getDBTableForTable("ClientRole");
        CONTRACT_CLIENT_DBTABLE   = DBTable.getDBTableForTable("ContractClient");
        SEGMENT_DBTABLE           = DBTable.getDBTableForTable("Segment");
        COMPANY_STRUCTURE_DBTABLE = DBTable.getDBTableForTable("ProductStructure");
        CONTRACT_GROUP_DBTABLE = DBTable.getDBTableForTable("ContractGroup");

        CLIENT_DETAIL_TABLENAME     = CLIENT_DETAIL_DBTABLE.getFullyQualifiedTableName();
        CLIENT_ROLE_TABLENAME       = CLIENT_ROLE_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_CLIENT_TABLENAME   = CONTRACT_CLIENT_DBTABLE.getFullyQualifiedTableName();
        SEGMENT_TABLENAME           = SEGMENT_DBTABLE.getFullyQualifiedTableName();
        COMPANY_STRUCTURE_TABLENAME = COMPANY_STRUCTURE_DBTABLE.getFullyQualifiedTableName();
        CONTRACT_GROUP_TABLENAME = CONTRACT_GROUP_DBTABLE.getFullyQualifiedTableName();
    }

    /**
     * Using the input of taxid buld a SearchResponseVO for the data to be returned.  Four Databases will be joined for this
     * request but only one database connection is needed to executed the request.
     * @param taxId
     * @return
     */
    public SearchResponseVO[] findClientsAndSegmentsByTaxID(String taxId)
    {

        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String clientIdentificationCol = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();
        String taxIdentificationCol    = CLIENT_DETAIL_DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();
        String lastNameCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String middleNameCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("MiddleName").getFullyQualifiedColumnName();
        String namePrefixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NamePrefix").getFullyQualifiedColumnName();
        String nameSuffixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NameSuffix").getFullyQualifiedColumnName();
        String corporateNameCol        = CLIENT_DETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
        String statusCTCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();
        String birthDateCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String clientRolePKCol         = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol           = CLIENT_ROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String clientDetailFKCol       = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String clientRoleReferenceCol  = CLIENT_ROLE_DBTABLE.getDBColumn("ReferenceID").getFullyQualifiedColumnName();

        String segmentPKCol            = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol       = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String productStructureFKCol   = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol      = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String optionCodeCTCol         = SEGMENT_DBTABLE.getDBColumn("OptionCodeCT").getFullyQualifiedColumnName();
        String contractGroupFKCol      = SEGMENT_DBTABLE.getDBColumn("ContractGroupFK").getFullyQualifiedColumnName();

        String segmentFKCol            = CONTRACT_CLIENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol         = CONTRACT_CLIENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String overrideStatusCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String businessContractNameCol = COMPANY_STRUCTURE_DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();
        String productStructurePKCol   = COMPANY_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String contractGroupPKCol	   = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupPK").getFullyQualifiedColumnName();
        String contractGroupNumberCol  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupNumber").getFullyQualifiedColumnName();
        
        String sql =   " SELECT " + clientDetailPKCol + ", " + clientIdentificationCol + ", " +
                       taxIdentificationCol + ", " + lastNameCol + ", " + firstNameCol + ", " +
                       middleNameCol + ", " + namePrefixCol + ", " + nameSuffixCol + ", " + birthDateCol + ", " +
                       corporateNameCol + ", " + statusCTCol + ", " + clientRolePKCol + ", " + roleTypeCTCol + ", " +
                       segmentPKCol + ", " + contractNumberCol + "," + productStructureFKCol + ", " +
                       segmentStatusCTCol + ", " + optionCodeCTCol + ", " + businessContractNameCol + ", " +
                       segmentFKCol + ", " + overrideStatusCol +  ", " + contractGroupNumberCol +
                       " FROM " + CLIENT_DETAIL_TABLENAME +
                       " LEFT OUTER JOIN " + CLIENT_ROLE_TABLENAME +
                       " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                       " LEFT OUTER JOIN " + CONTRACT_CLIENT_TABLENAME +
                       " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                       " LEFT OUTER JOIN " + SEGMENT_TABLENAME +
                       " ON " + segmentFKCol + " = " + segmentPKCol +
                       " LEFT OUTER JOIN " + COMPANY_STRUCTURE_TABLENAME +
                       " ON " + productStructureFKCol + " = " + productStructurePKCol +
                       
                       " LEFT OUTER JOIN " + CONTRACT_GROUP_TABLENAME +
                       " ON " + contractGroupPKCol + " = " + contractGroupFKCol +
                       
                       " WHERE (" + taxIdentificationCol + " = '" + taxId + "')" +
                       " ORDER BY " + lastNameCol + ", " + firstNameCol + ", " + corporateNameCol;

        List searchResponse = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            searchResponse = recurseThroughResultSet(rs, searchResponse);
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (searchResponse.size() == 0)
        {
            return null;
        }
        else
        {
            return ((SearchResponseVO[]) searchResponse.toArray(new SearchResponseVO[searchResponse.size()]));
        }
    }

   public SearchResponseVO[] findByContractNumber(String contractNumber)
   {
       Connection conn = null;
       Statement s = null;
       ResultSet rs = null;

       String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
       String clientIdentificationCol = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();
       String taxIdentificationCol    = CLIENT_DETAIL_DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();
       String lastNameCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
       String firstNameCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
       String middleNameCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("MiddleName").getFullyQualifiedColumnName();
       String namePrefixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NamePrefix").getFullyQualifiedColumnName();
       String nameSuffixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NameSuffix").getFullyQualifiedColumnName();
       String corporateNameCol        = CLIENT_DETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
       String statusCTCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();
       String birthDateCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

       String clientRolePKCol         = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
       String roleTypeCTCol           = CLIENT_ROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
       String clientDetailFKCol       = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();
        String clientRoleReferenceCol  = CLIENT_ROLE_DBTABLE.getDBColumn("ReferenceID").getFullyQualifiedColumnName();

       String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
       String segmentFKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
       String contractNumberCol     = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
       String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
       String segmentStatusCTCol    = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
       String optionCodeCTCol       = SEGMENT_DBTABLE.getDBColumn("OptionCodeCT").getFullyQualifiedColumnName();
        String contractGroupFKCol       = SEGMENT_DBTABLE.getDBColumn("ContractGroupFK").getFullyQualifiedColumnName();

       String segmentFKContractClientTableCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
       String clientRoleFKCol                 = CONTRACT_CLIENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
       String overrideStatusCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

       String businessContractNameCol         = COMPANY_STRUCTURE_DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();
       String productStructurePKCol           = COMPANY_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String contractGroupPKCol			  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupPK").getFullyQualifiedColumnName();
        String contractGroupNumberCol		  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupNumber").getFullyQualifiedColumnName();

       String segmentFKColIn = "";

       String baseSegmentSQL = "SELECT " + segmentPKCol + " FROM " + SEGMENT_TABLENAME +
                               " WHERE UPPER(" + contractNumberCol + ") = UPPER('" + contractNumber + "')" +
                               " AND " + segmentFKCol + " IS NULL";

       List searchResponse = new ArrayList();

       try
       {
           conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

           s = conn.createStatement();

           rs = s.executeQuery(baseSegmentSQL);

           long segmentPK = 0;

           while (rs.next())
           {
               segmentPK = rs.getLong("SegmentPK");
           }

           segmentFKColIn = " IN(" + segmentPK;

           Segment[] segments = Segment.findBy_SegmentFK(new Long(segmentPK));
           for (int i = 0; i < segments.length; i++)
           {
               segmentFKColIn = segmentFKColIn + ", " + segments[i].getSegmentPK();
           }

           segmentFKColIn = segmentFKColIn + ")";

           String sql =  " SELECT " + clientDetailPKCol + ", " + clientIdentificationCol + ", " +
                     taxIdentificationCol + ", " + lastNameCol + ", " + firstNameCol + ", " +
                     middleNameCol + ", " + namePrefixCol + ", " + nameSuffixCol + ", " + birthDateCol + ", " +
                     corporateNameCol + ", " + statusCTCol + ", " + clientRolePKCol + ", " + roleTypeCTCol + ", " +
                     segmentPKCol + ", " + contractNumberCol + ", " + productStructureFKCol + ", " +
                     segmentStatusCTCol + ", " + optionCodeCTCol + ", " + businessContractNameCol + ", " +
                      segmentPKCol + ", " + overrideStatusCol +  ", " + contractGroupNumberCol +
                      " FROM " + CLIENT_DETAIL_TABLENAME + ", " + CLIENT_ROLE_TABLENAME + ", " + SEGMENT_TABLENAME + ", " + CONTRACT_CLIENT_TABLENAME + ", " + COMPANY_STRUCTURE_TABLENAME + ", " + CONTRACT_GROUP_TABLENAME +
                     " WHERE UPPER(" + contractNumberCol + ") = UPPER('" + contractNumber + "')" +
                     " AND " + segmentFKContractClientTableCol + segmentFKColIn +
                     " AND " + clientRoleFKCol + " = " + clientRolePKCol +
                     " AND " + clientDetailPKCol + " = " + clientDetailFKCol +
                     " AND " + productStructureFKCol + " = " + productStructurePKCol +
                      " AND " + contractGroupPKCol + " = " + contractGroupFKCol +
                     " AND " + overrideStatusCol + " NOT IN ('D')" +
                     " AND " + segmentFKCol + " IS NULL " +
                     " ORDER BY " + lastNameCol + ", " + firstNameCol + ", " + corporateNameCol;

           s = conn.createStatement();

           rs = s.executeQuery(sql);

           searchResponse = recurseThroughResultSet(rs, searchResponse);
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
       }

       if (searchResponse.size() == 0)
       {
            return null;
       }
       else
       {
            return ((SearchResponseVO[]) searchResponse.toArray(new SearchResponseVO[searchResponse.size()]));
       }
   }

   public SearchResponseVO[] findByProductStructureContractNumber(long productStructurePK, String contractNumber)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String clientIdentificationCol = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();
        String taxIdentificationCol    = CLIENT_DETAIL_DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();
        String lastNameCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String middleNameCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("MiddleName").getFullyQualifiedColumnName();
        String namePrefixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NamePrefix").getFullyQualifiedColumnName();
        String nameSuffixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NameSuffix").getFullyQualifiedColumnName();
        String corporateNameCol        = CLIENT_DETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
        String statusCTCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();
        String birthDateCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String clientRolePKCol   = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol     = CLIENT_ROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String clientDetailFKCol = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();


        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol     = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol    = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String optionCodeCTCol       = SEGMENT_DBTABLE.getDBColumn("OptionCodeCT").getFullyQualifiedColumnName();
        String contractGroupFKCol    = SEGMENT_DBTABLE.getDBColumn("ContractGroupFK").getFullyQualifiedColumnName();

        String segmentFKContractClientTableCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol                 = CONTRACT_CLIENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String overrideStatusCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String businessContractNameCol = COMPANY_STRUCTURE_DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();

        String contractGroupPKCol			  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupPK").getFullyQualifiedColumnName();
        String contractGroupNumberCol		  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupNumber").getFullyQualifiedColumnName();
        
        String sql = " SELECT " + clientDetailPKCol + ", " + clientIdentificationCol + ", " +
                     taxIdentificationCol + ", " + lastNameCol + ", " + firstNameCol + ", " +
                     middleNameCol + ", " + namePrefixCol + ", " + nameSuffixCol + ", " + birthDateCol + ", " +
                     corporateNameCol + ", " + statusCTCol + ", " + clientRolePKCol + ", " + roleTypeCTCol + ", " +
                     segmentPKCol + ", " + contractNumberCol + ", " + productStructureFKCol + ", " +
                     segmentStatusCTCol + ", " + optionCodeCTCol + ", " + businessContractNameCol + ", " +
                     segmentFKContractClientTableCol + ", " + overrideStatusCol + ", " + contractGroupNumberCol + 
                     " FROM " + CLIENT_DETAIL_TABLENAME + ", " + CLIENT_ROLE_TABLENAME + ", " + SEGMENT_TABLENAME + ", " + CONTRACT_CLIENT_TABLENAME + ", " + COMPANY_STRUCTURE_TABLENAME  + ", " + CONTRACT_GROUP_TABLENAME +
                     " WHERE " + contractNumberCol + " = '" + contractNumber + "'" +
                     " AND " + segmentPKCol + " = " + segmentFKContractClientTableCol +
                     " AND " + clientRoleFKCol + " = " + clientRolePKCol +
                     " AND " + clientDetailPKCol + " = " + clientDetailFKCol +
                     " AND " + productStructureFKCol + " = " + productStructurePK +
                     " AND " + contractGroupPKCol + " = " + contractGroupFKCol +
                     " ORDER BY " + lastNameCol + ", " + firstNameCol + ", " + corporateNameCol;

        List searchResponse = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            searchResponse = recurseThroughResultSet(rs, searchResponse);
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (searchResponse.size() == 0)
        {
            return null;
        }
        else
        {
            return ((SearchResponseVO[]) searchResponse.toArray(new SearchResponseVO[searchResponse.size()]));
        }
    }

   public SearchResponseVO[] findByLastNamePartialFirstName(String lastName, String partialFirstName)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String clientIdentificationCol = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();
        String taxIdentificationCol    = CLIENT_DETAIL_DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();
        String lastNameCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String middleNameCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("MiddleName").getFullyQualifiedColumnName();
        String namePrefixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NamePrefix").getFullyQualifiedColumnName();
        String nameSuffixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NameSuffix").getFullyQualifiedColumnName();
        String corporateNameCol        = CLIENT_DETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
        String statusCTCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();
        String birthDateCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String clientRolePKCol   = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol     = CLIENT_ROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String clientDetailFKCol = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String segmentPKCol          = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol     = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String productStructureFKCol = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol    = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String optionCodeCTCol       = SEGMENT_DBTABLE.getDBColumn("OptionCodeCT").getFullyQualifiedColumnName();
        String contractGroupFKCol    = SEGMENT_DBTABLE.getDBColumn("ContractGroupFK").getFullyQualifiedColumnName();

        String segmentFKContractClientTableCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol                 = CONTRACT_CLIENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String overrideStatusCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String businessContractNameCol = COMPANY_STRUCTURE_DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();
        String productStructurePKCol   = COMPANY_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String contractGroupPKCol			  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupPK").getFullyQualifiedColumnName();
        String contractGroupNumberCol		  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupNumber").getFullyQualifiedColumnName();

        String sql = " SELECT " + clientDetailPKCol + ", " + clientIdentificationCol + ", " +
                     taxIdentificationCol + ", " + lastNameCol + ", " + firstNameCol + ", " +
                     middleNameCol + ", " + namePrefixCol + ", " + nameSuffixCol + ", " + birthDateCol + ", " +
                     corporateNameCol + ", " + statusCTCol + ", " + clientRolePKCol + ", " + roleTypeCTCol + ", " +
                     segmentPKCol + ", " + contractNumberCol + ", " + productStructureFKCol + ", " +
                     segmentStatusCTCol + ", " + optionCodeCTCol + ", " + businessContractNameCol + ", " +
                     segmentFKContractClientTableCol + ", " + overrideStatusCol + ", " + contractGroupNumberCol + 
                     " FROM " + CLIENT_DETAIL_TABLENAME +
                     " LEFT OUTER JOIN " + CLIENT_ROLE_TABLENAME +
                     " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                     " LEFT OUTER JOIN " + CONTRACT_CLIENT_TABLENAME +
                     " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                     " LEFT OUTER JOIN " + SEGMENT_TABLENAME +
                     " ON " + segmentFKContractClientTableCol + " = " + segmentPKCol +
                     " LEFT OUTER JOIN " + COMPANY_STRUCTURE_TABLENAME +
                     " ON " + productStructureFKCol + " = " + productStructurePKCol +
                     
                     " LEFT OUTER JOIN " + CONTRACT_GROUP_TABLENAME +
                     " ON " + contractGroupPKCol + " = " + contractGroupFKCol +
                     
                     " WHERE UPPER(" + lastNameCol + ") = UPPER('" + lastName + "')" +
                     " AND UPPER(" + firstNameCol + ") LIKE UPPER('%" + partialFirstName + "%')" +
                     " ORDER BY " + lastNameCol + ", " + firstNameCol + ", " + corporateNameCol;

       List searchResponse = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            searchResponse = recurseThroughResultSet(rs, searchResponse);

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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        if (searchResponse.size() == 0)
        {
            return null;
        }
        else
        {
            return ((SearchResponseVO[]) searchResponse.toArray(new SearchResponseVO[searchResponse.size()]));
        }
    }

    /**
     * Finds all clients whose lastName or productName begins with partialLastName
     *
     * NOTE:  This method uses ANSI Joins
     * @param partialLastName
     * @return query results
     * @
     */
	public SearchResponseVO[] findByPartialLastName(String partialLastName)
    {
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;
        List searchResponseVOs = new ArrayList();

        String clientDetailPKCol       = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientDetailPK").getFullyQualifiedColumnName();
        String clientIdentificationCol = CLIENT_DETAIL_DBTABLE.getDBColumn("ClientIdentification").getFullyQualifiedColumnName();
        String taxIdentificationCol    = CLIENT_DETAIL_DBTABLE.getDBColumn("TaxIdentification").getFullyQualifiedColumnName();
        String lastNameCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("LastName").getFullyQualifiedColumnName();
        String firstNameCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("FirstName").getFullyQualifiedColumnName();
        String middleNameCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("MiddleName").getFullyQualifiedColumnName();
        String namePrefixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NamePrefix").getFullyQualifiedColumnName();
        String nameSuffixCol           = CLIENT_DETAIL_DBTABLE.getDBColumn("NameSuffix").getFullyQualifiedColumnName();
        String corporateNameCol        = CLIENT_DETAIL_DBTABLE.getDBColumn("CorporateName").getFullyQualifiedColumnName();
        String statusCTCol             = CLIENT_DETAIL_DBTABLE.getDBColumn("StatusCT").getFullyQualifiedColumnName();
        String birthDateCol            = CLIENT_DETAIL_DBTABLE.getDBColumn("BirthDate").getFullyQualifiedColumnName();

        String clientRolePKCol         = CLIENT_ROLE_DBTABLE.getDBColumn("ClientRolePK").getFullyQualifiedColumnName();
        String roleTypeCTCol           = CLIENT_ROLE_DBTABLE.getDBColumn("RoleTypeCT").getFullyQualifiedColumnName();
        String clientDetailFKCol       = CLIENT_ROLE_DBTABLE.getDBColumn("ClientDetailFK").getFullyQualifiedColumnName();

        String segmentPKCol             = SEGMENT_DBTABLE.getDBColumn("SegmentPK").getFullyQualifiedColumnName();
        String contractNumberCol        = SEGMENT_DBTABLE.getDBColumn("ContractNumber").getFullyQualifiedColumnName();
        String productStructureFKCol    = SEGMENT_DBTABLE.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String segmentStatusCTCol       = SEGMENT_DBTABLE.getDBColumn("SegmentStatusCT").getFullyQualifiedColumnName();
        String optionCodeCTCol          = SEGMENT_DBTABLE.getDBColumn("OptionCodeCT").getFullyQualifiedColumnName();
        String segmentFKSegmentTableCol = SEGMENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String contractGroupFKCol    = SEGMENT_DBTABLE.getDBColumn("ContractGroupFK").getFullyQualifiedColumnName();

        String segmentFKContractClientTableCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("SegmentFK").getFullyQualifiedColumnName();
        String clientRoleFKCol                 = CONTRACT_CLIENT_DBTABLE.getDBColumn("ClientRoleFK").getFullyQualifiedColumnName();
        String overrideStatusCol = CONTRACT_CLIENT_DBTABLE.getDBColumn("OverrideStatus").getFullyQualifiedColumnName();

        String businessContractNameCol = COMPANY_STRUCTURE_DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();
        String productStructurePKCol   = COMPANY_STRUCTURE_DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String contractGroupPKCol			  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupPK").getFullyQualifiedColumnName();
        String contractGroupNumberCol		  = CONTRACT_GROUP_DBTABLE.getDBColumn("ContractGroupNumber").getFullyQualifiedColumnName();

        String sql = " SELECT " + clientDetailPKCol + ", " + clientIdentificationCol + ", " +
                     taxIdentificationCol + ", " + lastNameCol + ", " + firstNameCol + ", " +
                     middleNameCol + ", " + namePrefixCol + ", " + nameSuffixCol + ", " + birthDateCol + ", " +
                     corporateNameCol + ", " + statusCTCol + ", " + clientRolePKCol + ", " + roleTypeCTCol + ", " +
                     segmentPKCol + ", " + contractNumberCol + ", " + productStructureFKCol + ", " +
                     segmentStatusCTCol + ", " + optionCodeCTCol + ", " + businessContractNameCol + ", " +
                     overrideStatusCol + ", " + contractGroupNumberCol + 
                     " FROM " + CLIENT_DETAIL_TABLENAME +
                     " LEFT OUTER JOIN " + CLIENT_ROLE_TABLENAME +
                     " ON " + clientDetailPKCol + " = " + clientDetailFKCol +
                     " LEFT OUTER JOIN " + CONTRACT_CLIENT_TABLENAME +
                     " ON " + clientRolePKCol + " = " + clientRoleFKCol +
                     " LEFT OUTER JOIN " + SEGMENT_TABLENAME +
                     " ON " + segmentFKContractClientTableCol + " = " + segmentPKCol +
                     " LEFT OUTER JOIN " + COMPANY_STRUCTURE_TABLENAME +
                     " ON " + productStructureFKCol + " = " + productStructurePKCol +
                     
                     " LEFT OUTER JOIN " + CONTRACT_GROUP_TABLENAME +
                     " ON " + contractGroupPKCol + " = " + contractGroupFKCol +
                     
                     " WHERE (UPPER(" + lastNameCol + ") LIKE UPPER ('" + partialLastName + "%')" +
                     " OR UPPER(" + corporateNameCol + ") LIKE UPPER ('" + partialLastName + "%'))";

        List searchResponse = new ArrayList();

        try
        {
            conn = ConnectionFactory.getSingleton().getConnection(POOLNAME);

            s = conn.createStatement();

            rs = s.executeQuery(sql);

            searchResponse = recurseThroughResultSet(rs, searchResponse);
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
                if (rs != null) rs.close();
                if (s != null) s.close();
                if (conn != null) conn.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);

                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

                throw new RuntimeException(e);
            }
        }

        return ((SearchResponseVO[]) searchResponse.toArray(new SearchResponseVO[searchResponse.size()]));
    }

    private List recurseThroughResultSet(ResultSet rs, List searchResponse) throws SQLException
    {
        long previousClientDetailFK = 0;
        String previousClientId = null;
        String previousTaxId = null;
        String previousClientName = null;
        String previousClientStatus = null;
        String previousDateOfBirth = null;
        String previousContractGroupNumber = null;

        List searchRespContractInfoList = new ArrayList();

        while (rs.next())
        {
            long clientDetailFK = rs.getLong("ClientDetailPK");

            if (previousClientDetailFK == 0)
            {
                previousClientDetailFK = clientDetailFK;
                previousClientId = rs.getString("ClientIdentification");
                previousTaxId = rs.getString("TaxIdentification");
                previousDateOfBirth = Util.initString(rs.getString("BirthDate"), "");
                previousContractGroupNumber = Util.initString(rs.getString("ContractGroupNumber"), "");
                String lastName = Util.initString(rs.getString("LastName"), "");
                String firstName = Util.initString(rs.getString("FirstName"), "");
                String middleName = Util.initString(rs.getString("MiddleName"), "");
                String namePrefix = Util.initString(rs.getString("NamePrefix"), "");
                String nameSuffix = Util.initString(rs.getString("NameSuffix"), "");
                String corporateName = Util.initString(rs.getString("CorporateName"), "");
                if (lastName.equals(""))
                {
                    previousClientName = corporateName.trim();
                }
                else
                {
                    previousClientName = lastName + ", " + firstName.trim();
                    if (!middleName.equals(""))
                    {
                        previousClientName = previousClientName + ", " + middleName.trim();
                    }
                    if (!namePrefix.equals(""))
                    {
                        previousClientName = previousClientName + ", " + namePrefix.trim();
                    }
                    if (!nameSuffix.equals(""))
                    {
                        previousClientName = previousClientName + ", " + nameSuffix.trim();
                    }
                }

                previousClientStatus = Util.initString(rs.getString("StatusCT"), "");
                if (!previousDateOfBirth.equals(""))
                {
                    previousDateOfBirth = previousDateOfBirth.substring(0, 10);
                }
            }

            if (clientDetailFK != previousClientDetailFK)
            {
                SearchResponseVO searchResponseVO = new SearchResponseVO();

                searchResponseVO.setClientDetailFK(previousClientDetailFK);
                searchResponseVO.setClientIdentification(previousClientId);
                searchResponseVO.setTaxIdentification(previousTaxId);
                searchResponseVO.setClientName(previousClientName);
                searchResponseVO.setClientStatus(previousClientStatus);
                searchResponseVO.setContractGroupNumber(previousContractGroupNumber);
                searchResponseVO.setDateOfBirth(previousDateOfBirth);

                for (int i = 0; i < searchRespContractInfoList.size(); i++)
                {
                    SearchResponseContractInfo searchRespContractInfo = (SearchResponseContractInfo) searchRespContractInfoList.get(i);

                    searchResponseVO.addSearchResponseContractInfo(searchRespContractInfo);
                }

                searchResponse.add(searchResponseVO);

                //set "previous" values for next SearchResponseVO to be written
                previousClientDetailFK = clientDetailFK;
                previousClientDetailFK = clientDetailFK;
                previousClientId = rs.getString("ClientIdentification");
                previousTaxId = rs.getString("TaxIdentification");
                previousDateOfBirth = Util.initString(rs.getString("BirthDate"), "");
                previousContractGroupNumber = Util.initString(rs.getString("ContractGroupNumber"), "");
                String lastName = Util.initString(rs.getString("LastName"), "");
                String firstName = Util.initString(rs.getString("FirstName"), "");
                String middleName = Util.initString(rs.getString("MiddleName"), "");
                String namePrefix = Util.initString(rs.getString("NamePrefix"), "");
                String nameSuffix = Util.initString(rs.getString("NameSuffix"), "");
                String corporateName = Util.initString(rs.getString("CorporateName"), "");
                if (lastName.equals(""))
                {
                    previousClientName = corporateName.trim();
                }
                else
                {
                    previousClientName = lastName + ", " + firstName.trim();
                    if (!middleName.equals(""))
                    {
                        previousClientName = previousClientName + ", " + middleName.trim();
                    }
                    if (!namePrefix.equals(""))
                    {
                        previousClientName = previousClientName + ", " + namePrefix.trim();
                    }
                    if (!nameSuffix.equals(""))
                    {
                        previousClientName = previousClientName + ", " + nameSuffix.trim();
                    }
                }

                previousClientStatus = Util.initString(rs.getString("StatusCT"), "");
                if (!previousDateOfBirth.equals(""))
                {
                    previousDateOfBirth = previousDateOfBirth.substring(0, 10);
                }

                searchRespContractInfoList.clear();
            }

            long segmentPK = rs.getLong("SegmentPK");

            if (segmentPK > 0)
            {
                Segment segment = Segment.findByPK(segmentPK);
                String segmentStatus = segment.getSegmentStatusCT();
                if (segment.getSegmentFK() != null)
                {
                    segment = Segment.findByPK(segment.getSegmentFK());
                    segmentPK = segment.getSegmentPK().longValue();
                    segmentStatus = segment.getSegmentStatusCT();
                }

                SearchResponseContractInfo searchRespContractInfo = new SearchResponseContractInfo();

                searchRespContractInfo.setClientRoleFK(rs.getLong("ClientRolePK"));
                searchRespContractInfo.setRoleType(rs.getString("RoleTypeCT"));
                searchRespContractInfo.setSegmentFK(segmentPK);
                searchRespContractInfo.setContractNumber(rs.getString("ContractNumber"));
                searchRespContractInfo.setOptionCode(rs.getString("OptionCodeCT"));
                searchRespContractInfo.setProductStructureFK(rs.getLong("ProductStructureFK"));
                searchRespContractInfo.setBusinessContractName(rs.getString("BusinessContractName"));
                searchRespContractInfo.setSegmentStatus(segmentStatus);
                searchRespContractInfo.setOverrideStatus(Util.initString(rs.getString("OverrideStatus"), "Z"));

                searchRespContractInfoList.add(searchRespContractInfo);
            }
        }

        if (previousClientDetailFK > 0)
        {
            SearchResponseVO searchResponseVO = new SearchResponseVO();

            searchResponseVO.setClientDetailFK(previousClientDetailFK);
            searchResponseVO.setClientIdentification(previousClientId);
            searchResponseVO.setTaxIdentification(previousTaxId);
            searchResponseVO.setClientName(previousClientName);
            searchResponseVO.setClientStatus(previousClientStatus);
            searchResponseVO.setDateOfBirth(previousDateOfBirth);
            searchResponseVO.setContractGroupNumber(previousContractGroupNumber);

            for (int i = 0; i < searchRespContractInfoList.size(); i++)
            {
                SearchResponseContractInfo searchRespContractInfo = (SearchResponseContractInfo) searchRespContractInfoList.get(i);

                searchResponseVO.addSearchResponseContractInfo(searchRespContractInfo);
            }

            searchResponse.add(searchResponseVO);
        }

        return searchResponse;
    }
}