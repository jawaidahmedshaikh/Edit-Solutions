/*
 * Created by IntelliJ IDEA.
 * User: gfrosti
 * Date: Dec 31, 2002
 * Time: 11:40:34 AM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package security.business;

import edit.common.exceptions.*;
import edit.common.vo.*;
import edit.services.component.*;
import engine.*;

import java.util.*;

import security.*;

public interface Security extends ICRUD
{
    // Method below are being used by the new security system

    public void updatePassword(String username, String newPassword) throws EDITSecurityException;

    public String login(String username, String password) throws EDITSecurityException;

    public void logout(String sessionId);

    public void saveSecurityProfile(SecurityProfile securityProfile, PasswordMask passwordMask, Mask pMask, Mask operatorMask) throws EDITSecurityException;

    public SecurityProfile loadSecurityProfile();

    //public BIZComponentMethodVO[] getComponentMethods(long rolePK, String componentNameCT);

    public BIZComponentMethodVO[] getComponentMethods(long rolePK, String componentNameCT, long productStructurePK);

    public Role[] getAllRoles();

    public ProductStructureVO[] getAllProductTypeProductStructures();

    public ProductStructureVO[] getAttachedProductStructuresForRole(long rolePK);

    public void saveRole(String roleName) throws EDITSecurityException;

    public void deleteRole(long rolePK) throws EDITSecurityException;

    public BIZRoleVO[] getImpliedRoles(long rolePK);

    public void attachImpliedRole(Long rolePK, Long impliedRolePK) throws EDITSecurityException;

    public void detachImpliedRole(Long rolePK, Long impliedRolePK) throws EDITSecurityException;

    //    public void secureComponentMethod(
    //            long rolePK, long componentMethodPK, boolean isAuthorized) throws EDITSecurityException;

    public void secureComponentMethod(
            Long rolePK,
            long[] componentMethodPKs,
            Long productStructurePK,
            String[] componentMethodYNs) throws EDITSecurityException;

    public Operator getOperator(Long operatorPK, List voInclusionList);

    public Role getRole(long rolePK, List voInclusionList);

    public BIZRoleVO[] getOperatorsRoles(Long operatorPK);

    public Operator[] getAllOperators();
    
    public Operator[] getActiveOperators();

    public Password getCurrentPassword(long operatorPK);

    public Operator[] getOperatorsInRole(long rolePK);

    public String saveOperator(Operator operator, boolean operatorIsNew) throws EDITSecurityException;

    /**
     * sramamurthy 08/19/2004 the password reset funtionality for existing users
     * @param operator
     * @return
     * @throws EDITSecurityException
     */
    public String passworddReset(Operator operator) throws EDITSecurityException;

    public void deleteOperator(Long operatorPK) throws EDITSecurityException;

    public void attachOperatorToRole(Long operatorPK, Long rolePK) throws EDITSecurityException;

    public void detachOperatorFromRole(Long operatorPK, Long rolePK) throws EDITSecurityException;

    public SecurityLog[] getSecurityLogs();

    public boolean authorize(String sessionId,
                             String className,
                             String methodName,
                             ProductStructure currentProductStructure);

    public boolean securityIsInitialized();

    public String[] getRoleNames(String sessionId);

    public void loadInitialSecurity() throws EDITSecurityException;

    /**
     * Return true if there are product-type product structures and some
     * of the FilteredRoles point to non-existent product structures
     * @return
     */
    public boolean isSecurityDbMismatch();

    public void handleDbMismatch() throws EDITSecurityException;

    /**
     * Purpose of this is to check that the FilteredRole table has
     * been defined in the database.  If not, we can put out a
     * more meaningful error message.
     * @return
     */
    public boolean isFilteredRoleDefinedInDatabase();

    /**
     * True if security is enabled (and active).
     * @return
     */
    public boolean isSecurityEnabled();

    /**
     * Returns all Product Structures.
     * @return
     */
    public ProductStructureVO[] getAllProductStructures();
    
    /**
     * Returns all "Product" productStructures AND the Security product structure
     * @return
     */
    public ProductStructureVO[] getProductStructuresForRoleAttachment();

    /**
     * Attaches role to productstructures.
     * @param rolePK
     * @param productStructurePKs
     * @param cloneFromPKStr
     * @throws EDITSecurityException
     */
    public void attachRoleToProductStructures(Long rolePK, long[] productStructurePKs, String cloneFromPKStr) throws EDITSecurityException;
    
    /**
     * Detaches role from productstructures.
     * @param rolePK
     * @param productStructurePKs
     * @throws EDITSecurityException
     */
    public void detachRoleFromPoductStructures(Long rolePK, long[] productStructurePKs) throws EDITSecurityException;
    
    /**
     * 
     * @param sessionId
     * @param componentClassName
     * @param methodName
     * @return
     */
    public boolean authorize(String sessionId, String componentClassName, String methodName);
}
