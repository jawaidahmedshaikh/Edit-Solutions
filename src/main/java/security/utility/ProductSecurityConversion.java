package security.utility;

import engine.dm.dao.ProductStructureDAO;
import engine.ProductStructure;

import java.util.*;

import edit.common.vo.ProductStructureVO;
import edit.common.vo.SecuredMethodVO;
import edit.common.vo.RoleVO;
import edit.common.vo.FilteredRoleVO;
import edit.common.exceptions.EDITSecurityException;
import edit.services.db.CRUDEntity;
import edit.services.db.CRUDFactory;
import edit.services.db.ConnectionFactory;
import edit.services.db.CRUD;
import security.dm.dao.SecuredMethodDAO;
import security.dm.dao.RoleDAO;
import security.FilteredRole;
import security.SecuredMethod;

/**
 * A conversion utility that can detect if the database is
 * using pre-ProductSecurity and can also convert it.
 * <PRE>
 *     usage:
 *
 *     ProductSecurityConversion p = new ProductSecurityConversion();
 *     if (p.isConversionToProductSecurityNeeded())
 *     {
 *        p.convertDataToProductSecurity();
 *     }
 * </PRE>
 * <p/>
 * Copyright 2005 Systems Engineering Group, LLC.
 */
public class ProductSecurityConversion
{

    public static final boolean LOGGING_ON = true;

    private Map mapOfCompStructuresByPKs = new HashMap();

    /**
     * Each entry is keyed by RolePK and is a List of the
     * secured methods.
     */
    private Map mapOfSecuredMethodListsbyRolePKs = new HashMap();

    /**
     * doing this for logging a debugging purposes
     */
    private Map mapOfRoleNameByRolePK = new HashMap();

    /**
     * CRUDEntityImpl does not return the newly created PK but we need it
     * for this database conversion.  So we have a custom routine to save
     * the new FilteredRole rows.
     */
//    private CRUD crud = null;

    public ProductSecurityConversion()
    {
    }

    /**
     * Is a conversion needed?
     * @return
     */
    public boolean isConversionToProductSecurityNeeded()
    {
        // are there any Product company structures?
        // if no, then store WILDCARD company structure
        if (this.mapOfCompStructuresByPKs.size() == 0)
        {
            readInAllProductStructures();
        }

        FilteredRoleVO[] filteredRoles =
                security.dm.dao.DAOFactory.getFilteredRoleDAO().findAll();

        SecuredMethodVO[] securedMethods =
                security.dm.dao.DAOFactory.getSecuredMethodDAO().findAll();


        // check if no filtered roles and there are secured methods
        // pointing to Roles, then true
        if(filteredRoles == null || filteredRoles.length ==0)
        {
            if (securedMethods == null || securedMethods.length==0)
            {
                // no secured methods?  not much is set up
                return false;
            }
            else
            {
                long roleFK = securedMethods[0].getRoleFK();
                if (roleFK == 0)
                {
                    return false;    // this should not happen
                }
                else
                {
                    return true;    // no filtered roles plus old sec methods
                }
            }
        }
        else
        {
            return false;
        }
     }

    /**
     * Controls the conversion.
     */
    public void convertDataToProductSecurity()
    {
        log("---------------------------------------------------");
        log("Product Security Conversion started at " +
                (new Date()));
        try
        {
            // get all of our data in maps
            if (this.mapOfCompStructuresByPKs.size() == 0)
            {
                readInAllProductStructures();
            }

            if (this.mapOfCompStructuresByPKs.size() == 0)
            {
                ProductStructure defProductStructure =
                        ProductStructure.createDefaultForProduct();

                ProductStructureVO companyStructureVO =
                    (ProductStructureVO) defProductStructure.getVO();

                saveProductStructureInMap(companyStructureVO);
            }

            readInAllRolesAndSecuredMethods();

            // now convert it
            storeNewData();

            // delete old data
            deleteOldSecuredMethodRows();
        }
        catch (Exception ex)
        {
            log("! Product Security Conversion had exception at " +
                (new Date()));
            log("---------------------------------------------------");
            
           System.out.println(ex);

            ex.printStackTrace();
            throw new RuntimeException(ex);
        }

        log("Product Security Conversion finished normally at " +
                (new Date()));
        log("---------------------------------------------------");
    }

    private void deleteOldSecuredMethodRows() throws Exception
    {
        int count = 0;

        SecuredMethodVO[] securedMethodVOs =
                security.dm.dao.DAOFactory.getSecuredMethodDAO().findAll();

        for (int i = 0; i < securedMethodVOs.length; i++)
        {
            SecuredMethodVO securedMethodVO = securedMethodVOs[i];

            if (securedMethodVO.getRoleFK() == 0)
            {
                continue;
            }

//            SecuredMethod securedMethod = new SecuredMethod(securedMethodVO);
//            //log("\tdeleting old SecuredMethod pk=" + securedMethodVO.getSecuredMethodPK());
//            securedMethod.delete();
            count++;
        }

        //log("\nDeleted " + count + " old SecuredMethod rows\n");
    }


    private void readInAllProductStructures()
    {
        ProductStructureDAO dao =
                engine.dm.dao.DAOFactory.getProductStructureDAO();

        ProductStructure[] companyStructures = ProductStructure.findAllProductType();

        for (int i = 0; i < companyStructures.length; i++)
        {
            ProductStructure companyStructure = companyStructures[i];
            ProductStructureVO companyStructureVO =
                (ProductStructureVO) companyStructure.getVO();

            saveProductStructureInMap(companyStructureVO);
        }
    }

    private void saveProductStructureInMap(ProductStructureVO companyStructureVO)
    {
        long pk = companyStructureVO.getProductStructurePK();

        this.mapOfCompStructuresByPKs.put(new Long(pk),
                companyStructureVO);
    }

    private void readInAllRolesAndSecuredMethods()
    {
        RoleDAO roleDAO = security.dm.dao.DAOFactory.getRoleDAO();

        SecuredMethodDAO securedMethodDAO =
                security.dm.dao.DAOFactory.getSecuredMethodDAO();

        RoleVO[] allRoleVOs = roleDAO.findAll();

        for (int i = 0; i < allRoleVOs.length; i++)
        {
            RoleVO allRoleVO = allRoleVOs[i];
            long rolePK = allRoleVO.getRolePK();
            String roleName = allRoleVO.getName();

            // we'll keep the PK-->role name for debugging
            storeRoleNameByPK(rolePK, roleName);

            SecuredMethodVO[] securedMethodVOsForRole =
                    securedMethodDAO.findByRolePK_DBConversionOnly(rolePK);

            if (securedMethodVOsForRole == null)
            {
                continue;
            }

            for (int j = 0; j < securedMethodVOsForRole.length; j++)
            {
                SecuredMethodVO securedMethodVO =
                        securedMethodVOsForRole[j];

                long securedMethodPK =
                        securedMethodVO.getSecuredMethodPK();

                long componentMethodFK =
                        securedMethodVO.getComponentMethodFK();

                saveMethodInMap(rolePK,
                        roleName,
                        securedMethodPK,
                        componentMethodFK);
            }
        }
    }

    private void saveMethodInMap(long roleFK,
                                 String roleName,
                                 long securedMethodPK,
                                 long componentMethodFK)
    {

        SecuredMethodVO securedMethodVO = new SecuredMethodVO();


        securedMethodVO.setRoleFK(roleFK);
        securedMethodVO.setSecuredMethodPK(securedMethodPK);
        securedMethodVO.setComponentMethodFK(componentMethodFK);

        if (this.mapOfSecuredMethodListsbyRolePKs.
                containsKey(new Long(roleFK)))
        {
            List listOfSecuredMethodVOs =
                    (List) this.mapOfSecuredMethodListsbyRolePKs.
                    get(new Long(roleFK));

            listOfSecuredMethodVOs.add(securedMethodVO);
        }
        else
        {
            List listOfSecuredMethodVOs =
                    new ArrayList();
            listOfSecuredMethodVOs.add(securedMethodVO);
            this.mapOfSecuredMethodListsbyRolePKs.
                    put(new Long(roleFK), listOfSecuredMethodVOs);

        }
    }

    private void storeNewData() throws EDITSecurityException
    {
        // get next company structure
        Iterator itCompStructuresFKs = this.mapOfCompStructuresByPKs.keySet().iterator();

        while (itCompStructuresFKs.hasNext())
        {
            Long companyStrucLong = (Long) itCompStructuresFKs.next();
            long companyStructureFK = companyStrucLong.longValue();
            ProductStructureVO companyStructureVO =
                    (ProductStructureVO) this.mapOfCompStructuresByPKs.
                    get(companyStrucLong);
            String companyStructureName =
                    companyStructureVO.getBusinessContractName();

            log("\nStarting updates for ProductStructure=" +
                    companyStructureName);

            // get the iterator all over again
            Iterator itListsOfSecuredMethodRoleFKs =
                    this.mapOfSecuredMethodListsbyRolePKs.keySet().iterator();

            while (itListsOfSecuredMethodRoleFKs.hasNext())
            {
                Long roleLong = (Long) itListsOfSecuredMethodRoleFKs.next();
                long roleFK = roleLong.longValue();

                List listOfSecuredMethodsForRole =
                        (List) this.mapOfSecuredMethodListsbyRolePKs.
                        get(roleLong);

                String roleName = getRoleNameByPK(roleFK);

                log("\tRole=" + roleName);

                FilteredRoleVO filteredRoleVO = new FilteredRoleVO();
                //filteredRoleVO.setFilteredRolePK(0);
                filteredRoleVO.setProductStructureFK(companyStructureFK);
                filteredRoleVO.setRoleFK(roleFK);

                // insert the FilteredRole
                FilteredRole filteredRole = new FilteredRole();
                long newFilteredRolePK =
                        insertFilteredRole(filteredRole);  // WE NEED A SPECIAL INSERT
                //filteredRole.save();
                //log("\tinserted new FilteredRole for Role/CompStruct, pk=" + newFilteredRolePK);

                int securedMethodInsertCount = 0;

                for (int i = 0; i < listOfSecuredMethodsForRole.size(); i++)
                {
                    SecuredMethodVO securedMethodVOFromFile =
                            (SecuredMethodVO) listOfSecuredMethodsForRole.get(i);

                    securedMethodVOFromFile.getRoleFK();

                    long securedMethodPK = securedMethodVOFromFile.getSecuredMethodPK();

                    SecuredMethodVO newSecuredMethodVO = new SecuredMethodVO();

                    newSecuredMethodVO.setComponentMethodFK(securedMethodVOFromFile.getComponentMethodFK());

                    newSecuredMethodVO.setFilteredRoleFK(newFilteredRolePK);

                    SecuredMethod newSecuredMethod = new SecuredMethod();
                    newSecuredMethod.hSave();
                    //    log("\tinserted new SecuredMethod with FilteredRoleFK=" +
                    //            newFilteredRolePK +
                    //            ", ComponentMethodFK=" +
                    //            securedMethodVOFromFile.getComponentMethodFK());
                    securedMethodInsertCount++;
                }
                //                log("\tinserted " + securedMethodInsertCount +
                //                        " new SecuredMethods for the new FilteredRole");
            }
        }
    }

    private String getRoleNameByPK(long roleFK)
    {
        Long roleFKLong = new Long(roleFK);
        String roleName = (String) this.mapOfRoleNameByRolePK.get(roleFKLong);
        return roleName;
    }

    private void storeRoleNameByPK(long roleFK, String roleName)
    {
        Long roleFKLong = new Long(roleFK);

        if (!this.mapOfRoleNameByRolePK.containsKey(roleFKLong))
        {
            this.mapOfRoleNameByRolePK.put(roleFKLong, roleName);
        }
    }

    /**
     * We need to know the PK of the row just inserted.  This method
     * will allow for that.  CRUDEntityImpl doesn't return it.
     *
     * @param filteredRole
     * @return
     */
    private long insertFilteredRole(FilteredRole filteredRole)
    {
//        CRUDEntity crudEntity = filteredRole;

        CRUD crud = CRUDFactory.getSingleton().getCRUD(ConnectionFactory.SECURITY_POOL);
        long newPK = 0;
//        long newPK = crud.createOrUpdateVOInDB(crudEntity.getVO());

        if (crud != null) crud.close();

        return newPK;
    }

    private void log(String aString)
    {
        if (LOGGING_ON)
        {
            System.out.println(aString);
        }
    }
}