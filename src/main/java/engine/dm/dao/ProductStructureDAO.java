/*
 * ProductStructureDAO.java      Version 1.1  07/26/2001
 *
 * (c) 2000-2007 Systems Engineering Group, LLC.  All Rights Reserved
 * Systems Engineering Group, LLC Proprietary and confidential.  Any use is
 * subject to the license agreement.
 */
package engine.dm.dao;

import edit.common.vo.*;
import edit.services.db.*;

import java.util.*;

import engine.ProductStructure;
import security.Role;


public class ProductStructureDAO extends DAO
{
    private final String POOLNAME;
    private final DBTable DBTABLE;
    private final String TABLENAME;

    private final DBTable COMPANY_DBTABLE;
    private final String COMPANY_TABLENAME;


    public ProductStructureDAO()
    {
        POOLNAME  = ConnectionFactory.ENGINE_POOL;
        DBTABLE   = DBTable.getDBTableForTable("ProductStructure");
        TABLENAME = DBTABLE.getFullyQualifiedTableName();

        COMPANY_DBTABLE = DBTable.getDBTableForTable("Company");
        COMPANY_TABLENAME = COMPANY_DBTABLE.getFullyQualifiedTableName();
    }

    public ProductStructureVO[] findByPK(long productStructurePK, boolean includeChildVOs, List voExclusionList)
    {
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructurePKCol + " = " + productStructurePK;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }

    public ProductStructureVO[] findAll(boolean includeChildVOs, List voExclusionList)
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }

    public ProductStructureVO[] findAllProductTypeStructures(boolean includeChildVOs, List voExclusionList)
    {
        String typeCodeCTCol = DBTABLE.getDBColumn("TypeCodeCT").getFullyQualifiedColumnName();

        String sql = "SELECT * FROM " + TABLENAME +
                     " WHERE " + typeCodeCTCol + " = 'Product'";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }

    public ProductStructureVO[] findByProductStructureIds(long[] ids)
    {
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructurePKCol + " IN(";

        for (int i = 0; i < ids.length; i++) {

            if (i < ids.length - 1) {

                sql += ids[i];
                sql += ", ";
            }

            else {

                sql += ids[i];
            }
        }

        sql += ")";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }

    public ProductStructureVO[] findByRulesPK(long rulesPK, boolean includeChildVOs, List voExclusionList)
    {
        DBTable productRuleStructureDBTable = DBTable.getDBTableForTable("ProductRuleStructure");

        String productRuleStructureTable = productRuleStructureDBTable.getFullyQualifiedTableName();


        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String productStructureFKCol = productRuleStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String rulesFKCol            = productRuleStructureDBTable.getDBColumn("RulesFK").getFullyQualifiedColumnName();

        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME + ", " + productRuleStructureTable +
                     " WHERE " + productStructurePKCol + " = " + productStructureFKCol +
                     " AND " + rulesFKCol + " = " + rulesPK;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }

    public ProductStructureVO[] findByProductStructureId(long id)
    {
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + productStructurePKCol + " = " + id;

        List voExclusionList = new ArrayList();
        voExclusionList.add(ProductRuleStructureVO.class);
        voExclusionList.add(ProductFilteredFundStructureVO.class);

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }

    public ProductStructureVO[] findAllProductStructures()
    {
        String marketingPackageNameCol = DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();
        String groupProductNameCol     = DBTABLE.getDBColumn("GroupProductName").getFullyQualifiedColumnName();
        String areaNameCol             = DBTABLE.getDBColumn("AreaName").getFullyQualifiedColumnName();
        String businessContractNameCol = DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " ORDER BY " + marketingPackageNameCol +
                     ", " + groupProductNameCol + ", " + areaNameCol + ", " + businessContractNameCol;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);

    }

    public ProductStructureVO[] findProductStructureByNames(String mpName,
                                                            String gpName,
                                                            String areaName,
                                                            String bcName)
    {
        String marketingPackageNameCol = DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();
        String groupProductNameCol     = DBTABLE.getDBColumn("GroupProductName").getFullyQualifiedColumnName();
        String areaNameCol             = DBTABLE.getDBColumn("AreaName").getFullyQualifiedColumnName();
        String businessContractNameCol = DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + marketingPackageNameCol + " = '" + mpName + "'" +
                     " AND " + groupProductNameCol + " = '" + gpName + "'" +
                     " AND " + areaNameCol + " = '" + areaName + "'" +
                     " AND " + businessContractNameCol + " = '" + bcName + "'";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);

    }

    public ProductStructureVO[] findProductStructureByNames(String companyName,
                                                            String mpName,
                                                            String gpName,
                                                            String areaName,
                                                            String bcName)
    {
        String marketingPackageNameCol = DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();
        String groupProductNameCol     = DBTABLE.getDBColumn("GroupProductName").getFullyQualifiedColumnName();
        String areaNameCol             = DBTABLE.getDBColumn("AreaName").getFullyQualifiedColumnName();
        String businessContractNameCol = DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();
        String companyFKCol            = DBTABLE.getDBColumn("CompanyFK").getFullyQualifiedColumnName();

        String companyNameCol         = COMPANY_DBTABLE.getDBColumn("CompanyName").getFullyQualifiedColumnName();
        String companyPKCol           = COMPANY_DBTABLE.getDBColumn("CompanyPK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME + ", " + COMPANY_TABLENAME +
                     " WHERE " + marketingPackageNameCol + " = '" + mpName + "'" +
                     " AND " + groupProductNameCol + " = '" + gpName + "'" +
                     " AND " + areaNameCol + " = '" + areaName + "'" +
                     " AND " + businessContractNameCol + " = '" + bcName + "'" +
                     " AND " + companyNameCol + " = '" + companyName + "'" +
                     " AND " + companyPKCol + " = " + companyFKCol;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);

    }

    public ProductStructureVO[] findProductStructureByNames_CompanyFK(String mpName,
                                                                      String gpName,
                                                                      String areaName,
                                                                      String bcName,
                                                                      long companyFK)
    {
        String marketingPackageNameCol = DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();
        String groupProductNameCol     = DBTABLE.getDBColumn("GroupProductName").getFullyQualifiedColumnName();
        String areaNameCol             = DBTABLE.getDBColumn("AreaName").getFullyQualifiedColumnName();
        String businessContractNameCol = DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();
        String companyFKCol            = DBTABLE.getDBColumn("CompanyFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + marketingPackageNameCol + " = '" + mpName + "'" +
                     " AND " + groupProductNameCol + " = '" + gpName + "'" +
                     " AND " + areaNameCol + " = '" + areaName + "'" +
                     " AND " + businessContractNameCol + " = '" + bcName + "'" +
                     " AND " + companyFKCol + " = " + companyFK;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);

    }

    public ProductStructureVO[] findProductStructuresByMarketingPackage(String marketingPackageName)
    {
        String marketingPackageNameCol = DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE " + marketingPackageNameCol + " = '" + marketingPackageName + "'";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }

    public ProductStructureVO[] findAllProductStructuresAttachedToRules()
    {
        DBTable productRuleStructureDBTable = DBTable.getDBTableForTable("ProductRuleStructure");

        String productRuleStructureTable = productRuleStructureDBTable.getFullyQualifiedTableName();

        String productStructurePKCol   = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String marketingPackageNameCol = DBTABLE.getDBColumn("MarketingPackageName").getFullyQualifiedColumnName();
        String groupProductNameCol     = DBTABLE.getDBColumn("GroupProductName").getFullyQualifiedColumnName();
        String areaNameCol             = DBTABLE.getDBColumn("AreaName").getFullyQualifiedColumnName();
        String businessContractNameCol = DBTABLE.getDBColumn("BusinessContractName").getFullyQualifiedColumnName();

        String productStructureFKCol = productRuleStructureDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME +
                     " WHERE " + productStructurePKCol + " IN (SELECT " + productStructureFKCol +
                     " FROM " + productRuleStructureTable + ")" +
                     " ORDER BY " + marketingPackageNameCol + ", " + groupProductNameCol +
                     ", " + areaNameCol + ", " + businessContractNameCol;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }

    public ProductStructureVO[] findAllProductStructureNames()
    {
        String sql = " SELECT * FROM " + TABLENAME;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param areaValuePK
     * @return
     */
    public ProductStructureVO[] findBy_ProductStructurePK_AreaValuePK(long productStructurePK, long areaValuePK)
    {
        DBTable filteredAreaValueDBTable = DBTable.getDBTableForTable("FilteredAreaValue");
        String filteredAreaValueTable = filteredAreaValueDBTable.getFullyQualifiedTableName();

        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String productStructureFKCol = filteredAreaValueDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String areaValueFKCol = filteredAreaValueDBTable.getDBColumn("AreaValueFK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " INNER JOIN " + filteredAreaValueTable +
                " ON " + productStructurePKCol + " = " + productStructureFKCol +
                " WHERE " + productStructurePKCol + " = " + productStructurePK +
                " AND " + areaValueFKCol + " = " + areaValuePK;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    /**
     * Finder.
     * @param productStructurePK
     * @param treatyGroupPK
     * @return
     */
    public ProductStructureVO[] findBy_ProductStructurePK_TreatyGroupPK(long productStructurePK, long treatyGroupPK)
    {
        DBTable filteredTreatyGroupDBTable = DBTable.getDBTableForTable("FilteredTreatyGroup");
        String filteredTreatyGroupTable = filteredTreatyGroupDBTable.getFullyQualifiedTableName();
        String productStructureFKCol = filteredTreatyGroupDBTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();
        String treatyGroupFKCol = filteredTreatyGroupDBTable.getDBColumn("TreatyGroupFK").getFullyQualifiedColumnName();

        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                " INNER JOIN " + filteredTreatyGroupTable +
                " ON " + productStructurePKCol + " = " + productStructureFKCol +
                " WHERE " + productStructureFKCol + " = " + productStructurePK +
                " AND " + treatyGroupFKCol + " = " + treatyGroupPK;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                sql,
                POOLNAME,
                false,
                null);
    }

    public ProductStructureVO[] findByTypeCode(String typeCode, boolean includeChildVOs, List voExclusionList)
    {

        String typeCodeCTCol = DBTABLE.getDBColumn("TypeCodeCT").getFullyQualifiedColumnName();

        String sql = " SELECT * FROM " + TABLENAME +
                     " WHERE UPPER(" + typeCodeCTCol + ") = UPPER('" + typeCode + "')";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }


    public ProductStructureVO[] findForRole(long roleFK, boolean includeChildVOs, List voExclusionList)
    {
        // this schema
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();

        // security schema
        DBTable dbRoleTable   = DBTable.getDBTableForTable("Role");
        String roleTableName = dbRoleTable.getFullyQualifiedTableName();
        String roleRolePKCol = dbRoleTable.getDBColumn("RolePK").getFullyQualifiedColumnName();

        // security schema
        DBTable dbFilteredRoleTable = DBTable.getDBTableForTable("FilteredRole");
        String filteredRoleTableName = dbFilteredRoleTable.getFullyQualifiedTableName();
        String filteredRoleRoleFKCol = dbFilteredRoleTable.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String filteredRoleProductStructureFKCol =
                dbFilteredRoleTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT " + TABLENAME + ".* FROM " + TABLENAME + "," + roleTableName + "," + filteredRoleTableName +
                " WHERE " +
                productStructurePKCol + " = " + filteredRoleProductStructureFKCol +
                " AND " + filteredRoleRoleFKCol + " = " + roleRolePKCol +
                " AND " + roleRolePKCol + " = " + roleFK;

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }


    /**
     * Return array of distinct ProductStructureVO objects.  As long as the
     * operator has at least one FilteredRole for that ProductStructure
     * the ProductStructureVO will be included.
     * <p>
     * Note - this FILTERS for Product type Product Structures.
     * @param operatorPK
     * @return
     */
    public ProductStructureVO[] findProductTypeForOperator(long operatorPK)
    {
        // this schema
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String productStructureTypeCodeCTCol = DBTABLE.getDBColumn("TypeCodeCT").getFullyQualifiedColumnName();

        // security schema
        DBTable dbRoleTable   = DBTable.getDBTableForTable("Role");
        String roleTableName = dbRoleTable.getFullyQualifiedTableName();
        String roleRolePKCol = dbRoleTable.getDBColumn("RolePK").getFullyQualifiedColumnName();

        DBTable dbFilteredRoleTable = DBTable.getDBTableForTable("FilteredRole");
        String filteredRoleTableName = dbFilteredRoleTable.getFullyQualifiedTableName();
        String filteredRoleRoleFKCol = dbFilteredRoleTable.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        DBTable dbOperatorRoleTable = DBTable.getDBTableForTable("OperatorRole");
        String operatorRoleTableName = dbOperatorRoleTable.getFullyQualifiedTableName();
        String operatorRoleRoleFKCol = dbOperatorRoleTable.getDBColumn("RoleFK").getFullyQualifiedColumnName();
        String operatorRolePKCol = dbOperatorRoleTable.getDBColumn("OperatorRolePK").getFullyQualifiedColumnName();
        String operatorRoleOperatorFKCol = dbOperatorRoleTable.getDBColumn("OperatorFK").getFullyQualifiedColumnName();

        DBTable dbOperatorTable = DBTable.getDBTableForTable("Operator");
        String operatorTableName = dbOperatorTable.getFullyQualifiedTableName();
        String operatorOperatorPKCol = dbOperatorTable.getDBColumn("OperatorPK").getFullyQualifiedColumnName();


        String filteredRoleProductStructureFKCol =
                dbFilteredRoleTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " +
                TABLENAME + " WHERE " +
                productStructurePKCol +
                " IN (SELECT DISTINCT " + productStructurePKCol +
                    " FROM " +
                    TABLENAME + "," +
                    roleTableName + "," +
                    filteredRoleTableName + "," +
                    operatorRoleTableName + "," +
                    operatorTableName +
                    " WHERE " +
                    productStructurePKCol + " = " + filteredRoleProductStructureFKCol +
                    " AND UPPER(" + productStructureTypeCodeCTCol + ") = 'PRODUCT'" +
                    " AND " + filteredRoleRoleFKCol + " = " + roleRolePKCol +
                    " AND " + roleRolePKCol + " = " + operatorRoleRoleFKCol +
                    " AND " + operatorOperatorPKCol + " = " + operatorRoleOperatorFKCol +
                    " AND " + operatorOperatorPKCol + " = " + operatorPK
                + ")";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }


     /**
     * Return array of distinct ProductStructureVO objects for the role.
     * <p>
     * Note - this filters for Product Type Product Structures.
     * @param rolePK
     * @return
     */
    public ProductStructureVO[] findProductTypeForRole(long rolePK)
    {
        // this schema
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String productStructureTypeCodeCTCol = DBTABLE.getDBColumn("TypeCodeCT").getFullyQualifiedColumnName();

        // security schema
        DBTable dbRoleTable   = DBTable.getDBTableForTable("Role");
        String roleTableName = dbRoleTable.getFullyQualifiedTableName();
        String roleRolePKCol = dbRoleTable.getDBColumn("RolePK").getFullyQualifiedColumnName();

        DBTable dbFilteredRoleTable = DBTable.getDBTableForTable("FilteredRole");
        String filteredRoleTableName = dbFilteredRoleTable.getFullyQualifiedTableName();
        String filteredRoleRoleFKCol = dbFilteredRoleTable.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        String filteredRoleProductStructureFKCol =
                dbFilteredRoleTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();


        String sql = " SELECT * FROM " +
                TABLENAME + " WHERE " +
                productStructurePKCol +
                " IN (SELECT DISTINCT " + productStructurePKCol +
                    " FROM " +
                    TABLENAME + "," +
                    roleTableName + "," +
                    filteredRoleTableName +
                    " WHERE " +
                    productStructurePKCol + " = " + filteredRoleProductStructureFKCol +
                    " AND UPPER(" + productStructureTypeCodeCTCol + ") = 'PRODUCT'" +
                    " AND " + filteredRoleRoleFKCol + " = " + roleRolePKCol +
                    " AND " + roleRolePKCol + " = " + rolePK
                + ")";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);
    }


    public ProductStructureVO[] findProductTypeByRoles(Role[] implicitAndDirectRoles)
    {
        String productStructurePKCol = DBTABLE.getDBColumn("ProductStructurePK").getFullyQualifiedColumnName();
        String typeCodeCol = DBTABLE.getDBColumn("TypeCodeCT").getFullyQualifiedColumnName();

        // security schema
        DBTable dbRoleTable   = DBTable.getDBTableForTable("Role");
        String roleTableName = dbRoleTable.getFullyQualifiedTableName();
        String roleRolePKCol = dbRoleTable.getDBColumn("RolePK").getFullyQualifiedColumnName();

        // security schema
        DBTable dbFilteredRoleTable = DBTable.getDBTableForTable("FilteredRole");
        String filteredRoleTableName = dbFilteredRoleTable.getFullyQualifiedTableName();
        String filteredRoleRoleFKCol = dbFilteredRoleTable.getDBColumn("RoleFK").getFullyQualifiedColumnName();

        //Company schema
        DBTable dbCompanyTable = DBTable.getDBTableForTable("Company");
        String companyTableName = dbCompanyTable.getFullyQualifiedTableName();
        String companyNameCol = dbCompanyTable.getDBColumn("CompanyName").getFullyQualifiedColumnName();

        String filteredRoleProductStructureFKCol =
                dbFilteredRoleTable.getDBColumn("ProductStructureFK").getFullyQualifiedColumnName();

        StringBuffer roleFKSb = new StringBuffer(50);

        for (int i = 0; i < implicitAndDirectRoles.length; i++)
        {
            Role implicitAndDirectRole = implicitAndDirectRoles[i];
            long rolePK = implicitAndDirectRole.getRolePK().longValue();
            if (i > 0)
            {
                roleFKSb.append(" ,");
            }
            roleFKSb.append(rolePK);
        }

        String sql = " SELECT DISTINCT " + TABLENAME + ".* FROM " +
                TABLENAME + "," + companyTableName + ", " +
                roleTableName + "," +
                filteredRoleTableName +
                    " WHERE " +
                    productStructurePKCol + " = " + filteredRoleProductStructureFKCol +
                    " AND " + filteredRoleRoleFKCol + " = " + roleRolePKCol +
                    " AND " + roleRolePKCol + " IN ( " + roleFKSb + ")" +
                    " AND ((" + typeCodeCol + " = 'System'" +
                    " AND " + companyNameCol + " = 'Security')" +
                    " OR " + typeCodeCol + " =  'Product')";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   false,
                                                   null);

    }

    public ProductStructureVO[] findByTypeCodeforSecurity(String typeCode, boolean includeChildVOs, List voExclusionList)
    {

        String typeCodeCTCol = DBTABLE.getDBColumn("TypeCodeCT").getFullyQualifiedColumnName();
        String companyFKCol = DBTABLE.getDBColumn("CompanyFK").getFullyQualifiedColumnName();

        //Company schema
        DBTable dbCompanyTable = DBTable.getDBTableForTable("Company");
        String companyTableName = dbCompanyTable.getFullyQualifiedTableName();
        String companyNameCol = dbCompanyTable.getDBColumn("CompanyName").getFullyQualifiedColumnName();
        String companyPKCol = dbCompanyTable.getDBColumn("CompanyPK").getFullyQualifiedColumnName();

        String sql = " SELECT DISTINCT " + TABLENAME + ".* FROM " + TABLENAME +
                     " INNER JOIN " + companyTableName + " ON " + companyFKCol + " = " + companyPKCol +
                     " WHERE  ((" + typeCodeCTCol + " = 'System'" +
                     " AND " + companyNameCol + " = 'Security')" +
                     " OR " + typeCodeCTCol + " =  'Product')";

        return (ProductStructureVO[]) executeQuery(ProductStructureVO.class,
                                                   sql,
                                                   POOLNAME,
                                                   includeChildVOs,
                                                   voExclusionList);
    }
}